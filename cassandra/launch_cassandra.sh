
# 1. Launch Cassandra
/usr/local/bin/docker-entrypoint.sh cassandra -f &

# 2. Wait for Cassandra to be up
while ! cqlsh -e "describe cluster" > /dev/null 2>&1; do
	sleep 1
done

# # 3. Create tables in the database
cqlsh -f "$1"

# # 4. Signal Cassandra has been successfully launched
echo "Cassandra successfully launched."

# # 5. Make sure script does not end (so that container keeps running)
tail -f /dev/null

