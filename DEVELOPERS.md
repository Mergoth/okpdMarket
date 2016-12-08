#Developer knowledge.

## Run local cloud
From project root folder 

* `gradle clean build` 
* go to `webclient/`
* Run `ng build --prod`
* Copy `webclient/dist/**` to `gateway/src/main/resources/static`
* Run `sh dev-start.sh`
* Wait 1-2 minutes
* Open `http://$DOCKER_HOST`
    * To check logs: `docker-compose logs SERVICE_NAME_HERE`
* To stop: `docker-compose down`
## Debug microservice

`cd` into folder of needed microservice and run:

`gradle debug bootRun`

