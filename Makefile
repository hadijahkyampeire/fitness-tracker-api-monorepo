.PHONY: all user workout progress run clean stop

USER_SERVICE=user-service
WORKOUT_SERVICE=workout_service
PROGRESS_SERVICE=workout_progress_tracking
PORTS=8080 8081 8082 8083

# Default action: stop first, then run
all: run

run: stop
	@echo "Starting user-service..."
	cd $(USER_SERVICE) && mvn spring-boot:run &

	@echo "Starting workout-service..."
	cd $(WORKOUT_SERVICE) && mvn spring-boot:run &

	@echo "Starting progress-service..."
	cd $(PROGRESS_SERVICE) && mvn spring-boot:run &

	@echo "All services running. Press Ctrl+C to stop."

user:
	cd $(USER_SERVICE) && mvn spring-boot:run

workout:
	cd $(WORKOUT_SERVICE) && mvn spring-boot:run

progress:
	cd $(PROGRESS_SERVICE) && mvn spring-boot:run

clean:
	cd $(USER_SERVICE) && mvn clean
	cd $(WORKOUT_SERVICE) && mvn clean
	cd $(PROGRESS_SERVICE) && mvn clean

stop:
	@echo "Stopping all running Spring Boot services and freeing ports..."
	@pkill -f 'mvn spring-boot:run' || true
	@for port in $(PORTS); do \
		lsof -ti :$$port | xargs kill -9 2>/dev/null || true; \
	done
