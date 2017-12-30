
# 1. Launch Cassandra
cassandra -R -f > /dev/null & 

# 2. Wait for Cassandra to be up
while ! cqlsh -e "describe cluster" > /dev/null 2>&1; do
	sleep 1
done

# 3. Create tables in the database
cqlsh -f "$1"

# 4. Make sure script does not end (so that container keeps running)
tail -f /dev/null

