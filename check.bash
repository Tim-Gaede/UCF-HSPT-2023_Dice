g++ -std=c++17 -O2 ../../data/dice/check.cpp -o checker.out

echo Checking java
javac dice.java
for i in $(seq -f "%03g" 1 18)
do
	echo Checking $i...
    java dice < ../../data/dice/dice$i.in > res.out
	./checker.out ../../data/dice/dice$i.in res.out ../../data/dice/dice$i.out > diff.out
	if [ -s ./diff.out ]; then
		echo Failed $i
		cat diff.out
	fi
done
echo Checking c++
g++ -std=c++17 -O2 ./dice.cpp -o dice.out
for i in $(seq -f "%03g" 1 18)
do
	echo Checking $i...
    ./dice.out < ../../data/dice/dice$i.in > res.out
	./checker.out ../../data/dice/dice$i.in res.out ../../data/dice/dice$i.out > diff.out
	if [ -s ./diff.out ]; then
		echo Failed $i
		cat diff.out
	fi
done


rm checker.out
rm dice.out
rm dice.class
rm res.out
rm diff.out