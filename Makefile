repl:
	clojure -M:nREPL -m nrepl.cmdline

start:
	clojure -M:main

release:
	clojure -T:build uber

clean:
	clojure -T:build clean
