(ns dev.rameezkhan.wordle-solver.core
  (:gen-class)
  (:require [dev.rameezkhan.wordle-solver.wordle :as wordle]))

(= "perky"
   (:word
    (wordle/solve [[["a" :no-spot]
                    ["p" :incorrect-spot]
                    ["p" :no-spot]
                    ["l" :no-spot]
                    ["e" :incorrect-spot]]
                   [["t" :no-spot]
                    ["y" :incorrect-spot]
                    ["p" :incorrect-spot]
                    ["e" :incorrect-spot]
                    ["d" :no-spot]]
                   [["p" :correct-spot]
                    ["e" :correct-spot]
                    ["n" :no-spot]
                    ["n" :no-spot]
                    ["y" :correct-spot]]
                   [["p" :correct-spot]
                    ["e" :correct-spot]
                    ["r" :correct-spot]
                    ["k" :correct-spot]
                    ["y" :correct-spot]]
                   #_[["c" :no-spot]
                      ["o" :correct-spot]
                      ["u" :correct-spot]
                      ["n" :correct-spot]
                      ["t" :correct-spot]]]
                  wordle/words)))
