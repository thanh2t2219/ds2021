# Run instruction
cat word_input | python mapper.py | sort -k1,1 | python reducer.py

