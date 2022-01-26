(ns dev.rameezkhan.wordle-solver.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]))

(defn handler
  [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World!"})

(defn app-server-start
  []
  (jetty/run-jetty handler {:port 3000 :join? false}))

;; (def unknown-char "#")

;; (def words (sort (str/split-lines (slurp "resource/words.txt")))
;;   #_'("react"
;;       "crack"
;;       "plack"
;;       "shack"
;;       "whack"
;;       "apple"))

;; (def correct-word "whack")

;; (defn word?
;;   "Checks for valid word in wordlist"
;;   [word-list word]
;;   (not (nil? (some #{word} word-list))))

;; (def input [["r" :no-spot]
;;             ["e" :no-spot]
;;             ["a" :correct-spot]
;;             ["c" :correct-spot]
;;             ["t" :no-spot]])

;; (def input2 [["c" :no-spot]
;;             ["r" :no-spot]
;;             ["a" :correct-spot]
;;             ["c" :correct-spot]
;;             ["k" :correct-spot]])

;; (defn build-ordered-regex
;;   "Build an order regex from an input"
;;   [input]
;;   (reduce
;;    (fn
;;      [re-search [char spot]]
;;      (str
;;       re-search
;;       (if (= spot :correct-spot) char "."))) "" input))

;; (defn get-matches
;;   "Get a list of matches matching a search pattern"
;;   [word-list pattern]
;;   (filter #(re-find (re-pattern pattern) %) word-list))

;; (def re-search (build-ordered-regex input))


;; (re-find (re-pattern re-search) (second words))
;; (get-matches words re-search)



(defn -main
  [& args]
  (app-server-start))

;; (comment

;;   (def app-server-instance (-main))

;;   (.stop app-server-instance)

;;   )



