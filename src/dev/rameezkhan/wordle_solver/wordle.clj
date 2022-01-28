(ns dev.rameezkhan.wordle-solver.wordle
  (:require [clojure.string :as str]))

(defn- pos-char-for-spot-category
  "Get position for char that matches a specific spot category"
  [spot-category input]
  (->> input
       (map-indexed vector)
       (map (fn
              [[idx [char spot]]]
              (when
               (= spot spot-category)
                [idx char])))
       (remove nil?)))

(defn- get-pos-specific-exclusions
  "Get exclusions at a specific position"
  [input]
  (pos-char-for-spot-category :incorrect-spot input))

(defn- get-pos-specific-inclusions
  "Get inclusions at a specific position"
  [input]
  (pos-char-for-spot-category :correct-spot input))

(defn- get-any-pos-exclusions
  "Get exclusions at any position"
  [input]
  (->> input
       (map
        (fn
          [[char spot]]
          (when (= spot :no-spot) char)))
       (remove nil?)))

(defn- exclude-words-at-any-pos
  [exclusions words]
  (->> words
       (filter
        #(nil? (some (set (mapv first exclusions)) %)))))

(defn- exclude-words-by-char-at-pos
  [[pos char] words]
  (filter #(not= (nth % pos) (first char)) words))

(defn- include-words-by-char-at-pos
  [[pos char] words]
  (filter #(= (nth % pos) (first char)) words))

(defn- exclude-pos-specific-exclusions
  [exclusions words]
  (reduce #(exclude-words-by-char-at-pos %2 %1) words exclusions))

(defn- include-pos-specific-inclusions
  [inclusions words]
  (reduce #(include-words-by-char-at-pos %2 %1) words inclusions))

(defn- delete-elem-at-index
  [i char xs]
  (if (= (nth xs i) (first char))
    (keep-indexed #(when-not (= i %1) %2) xs) (vec xs)))

(defn- include-words-by-char-at-another-pos
  [[pos char] words]
  (filter #(not (nil? (some (set char) (delete-elem-at-index pos char %)))) words))

(defn- include-pos-specific-exclusions
  [exclusions words]
  (reduce #(include-words-by-char-at-another-pos %2 %1) words exclusions))

(defn- dedupe-any-exclusions-with-positional-inclusions
  [positional-inclusions exclusions]
  (reduce
   (fn
     [exclusions [_ char]]
     (filter
      #(not= char %) exclusions))
   exclusions
   positional-inclusions))

(defn- parse-result
  [words]
  (if (pos? (count words))
    (let [word (rand-nth words)
          possible-words (when (> (count words) 1) (take 10 (shuffle words)))
          confidence (float
                      (* (/ 1 (count words)) 100))]
      {:word word :confidence (format "%.2f" confidence) :other-possible-words possible-words})
    {:error "No words found"}))

(defn solve
  [input words]
  (when (pos? (count input))
    (loop [words                 words
           input                 input
           inclusions            []
           exclusions            []
           positional-exclusions []]
      (let [chosen-word                   (first input)
            current-inclusions            (get-pos-specific-inclusions chosen-word)
            current-exclusions            (get-any-pos-exclusions chosen-word)
            current-positional-exclusions (get-pos-specific-exclusions chosen-word)
            deduped-exclusions            (->> current-exclusions
                                               (dedupe-any-exclusions-with-positional-inclusions current-inclusions)
                                               (dedupe-any-exclusions-with-positional-inclusions current-positional-exclusions))
            inclusions                    (concat inclusions current-inclusions)
            exclusions                    (concat exclusions deduped-exclusions)
            positional-exclusions         (concat positional-exclusions current-positional-exclusions)
            filtered-words                (->> words
                                               (include-pos-specific-exclusions current-positional-exclusions)
                                               (exclude-words-at-any-pos exclusions)
                                               (include-pos-specific-inclusions inclusions)
                                               (exclude-pos-specific-exclusions positional-exclusions))]
        (if
         (> (count input) 1)
          (recur
           filtered-words
           (drop 1 input)
           inclusions
           exclusions
           positional-exclusions)
          (parse-result filtered-words))))))

(def words
  (sort (str/split-lines
         (slurp "resources/words.txt"))))
