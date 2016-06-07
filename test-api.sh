#!/bin/sh

deviceId="`hostname`-`date +%k%N`"
lastAccountId=`curl -s http://295061db.ngrok.io/rest/api/accounts | python -m json.tool | grep accountId | tail -1 | awk '{print $2}' | awk -F',' '{print $1}'`
lastTaskId=`curl -s http://295061db.ngrok.io/rest/api/tasks | python -m json.tool | grep taskId | tail -1 | awk '{print $2}' | awk -F',' '{print $1}'`
lastProjectId=`curl -s http://295061db.ngrok.io/rest/api/projects | python -m json.tool | grep projectId | tail -1 | awk '{print $2}' | awk -F',' '{print $1}'`
accountId=`expr $lastAccountId + 1`
taskId=`expr $lastTaskId + 1`
projectId=`expr $lastProjectId + 1`
sed "s/ACCOUNT_ID_TEMPLATE/$accountId/;s/DEVICE_ID_TEMPLATE/$deviceId/" account.template > account.json
sed "s/ACCOUNT_ID_TEMPLATE/$accountId/;s/PROJECT_ID_TEMPLATE/$projectId/" project.template > project.json
sed "s/ACCOUNT_JSON_TEMPLATE/$(sed -e 's/[\&/]/\\&/g' -e 's/$/\\n\t/' account.json | tr -d '\n')/;s/TASK_ID_TEMPLATE/$taskId/" task.template > task.json
sed "s/atanass/atanass-editted/" account.json > account-editted.json
sed "s/Demonstration task/Demonstration task - editted/" task.json > task-editted.json

projectLineNumber=`grep -n project task-editted.json | awk -F':' '{print $1}'`
sed "${projectLineNumber}s/null/$(sed -e 's/[\&/]/\\&/g' -e 's/$/\\n\t/' project.json | tr -d '\n')/" task-editted.json > task-editted-project.json

echo "exiting..."
exit 0;

echo "Getting all accounts..."
sleep 2
curl -s http://295061db.ngrok.io/rest/api/accounts | python -m json.tool
read -p "Press enter to continue"

echo "Creating a new account..."
sleep 2
curl -X POST http://295061db.ngrok.io/rest/api/accounts -H "Content-Type: application/json" -d "@account.json" | python -m json.tool
read -p "Press enter to continue"

echo "Get all accounts again..."
sleep 2
curl -s http://295061db.ngrok.io/rest/api/accounts | python -m json.tool
read -p "Press enter to continue"

echo "Editting the newly created acccount..."
sleep 2
curl -X POST http://295061db.ngrok.io/rest/api/accounts -H "Content-Type: application/json" -d "@account-editted.json" | python -m json.tool
read -p "Press enter to continue"

echo "Get all accounts again..."
sleep 2
curl -s http://295061db.ngrok.io/rest/api/accounts | python -m json.tool
read -p "Press enter to continue"

echo "Get a list of all tasks in the system..."
sleep 2
curl http://295061db.ngrok.io/rest/api/tasks | python -m json.tool
read -p "Press enter to continue"

echo "Creating a new task..."
sleep 2
curl -X POST http://295061db.ngrok.io/rest/api/tasks -H "Content-Type: application/json" -d "@task.json" | python -m json.tool
read -p "Press enter to continue"

echo "Get the newly created task..."
sleep 2
curl http://295061db.ngrok.io/rest/api/tasks/$taskId | python -m json.tool
read -p "Press enter to continue"

echo "Editting the newly created task..."
sleep 2
curl -X POST http://295061db.ngrok.io/rest/api/tasks -H "Content-Type: application/json" -d "@task-editted.json" | python -m json.tool
read -p "Press enter to continue"

echo "Get the newly created task again..."
sleep 2
curl http://295061db.ngrok.io/rest/api/tasks/$taskId | python -m json.tool
read -p "Press enter to continue"

echo "Get all projects..."
sleep 2
curl http://295061db.ngrok.io/rest/api/projects | python -m json.tool
read -p "Press enter to continue"

echo "Create a new project..."
sleep 2
curl -X POST http://295061db.ngrok.io/rest/api/projects -H "Content-Type: application/json" -d "@project.json"
read -p "Press enter to continue"

echo "Get the newly created project..."
sleep 2
curl http://295061db.ngrok.io/rest/api/projects/$projectId | python -m json.tool
read -p "Press enter to continue"

echo "Get all tasks in the newly created project..."
sleep 2
curl http://295061db.ngrok.io/rest/api/projects/$projectId/tasks | python -m json.tool
read -p "Press enter to continue"

echo "Adding the newly created task to the newly created project..."
sleep 2
curl -X POST http://295061db.ngrok.io/rest/api/tasks -H "Content-Type: application/json" -d "@task-editted-project.json" | python -m json.tool
read -p "Press enter to continue"

echo "Delete the newly created task..."
sleep 2
curl -X DELETE http://295061db.ngrok.io/rest/api/tasks/$taskId
read -p "Press enter to continue"

echo "Get the newly created task again..."
sleep 2
curl http://295061db.ngrok.io/rest/api/tasks/$taskId | python -m json.tool
read -p "Press enter to continue"

echo "Get all tasks in the newly created project..."
sleep 2
curl http://295061db.ngrok.io/rest/api/projects/$projectId/tasks | python -m json.tool
read -p "Press enter to continue"

echo "Verify that the task is deleted..."
sleep 2
curl http://295061db.ngrok.io/rest/api/tasks/$taskId | python -m json.tool




rm -f *.json
