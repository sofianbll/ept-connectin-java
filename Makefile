.PHONY: dev prod down down-dev reset reset-dev clean status logs logs-db shell f-install f-run

# ── Dev ──────────────────────────────────────────────────────────────────────
dev:
	docker compose -f docker-compose.dev.yml down && \
	docker compose -f docker-compose.dev.yml up --build --force-recreate

# ── Prod ─────────────────────────────────────────────────────────────────────
prod:
	docker compose down && \
	docker compose up --build --force-recreate

# ── Stop ─────────────────────────────────────────────────────────────────────
down:
	docker compose down

down-dev:
	docker compose -f docker-compose.dev.yml down

# ── Reset DB (garde images) ──────────────────────────────────────────────────
reset:
	docker compose down -v

reset-dev:
	docker compose -f docker-compose.dev.yml down -v

# ── Clean total ──────────────────────────────────────────────────────────────
clean:
	docker compose down -v --rmi all && \
	docker compose -f docker-compose.dev.yml down -v --rmi all && \
	docker image prune -f

# ── Status ───────────────────────────────────────────────────────────────────
status:
	@echo "=== Containers ===" && docker ps -a
	@echo "" && echo "=== Images ===" && docker images
	@echo "" && echo "=== Volumes ===" && docker volume ls

# ── Logs ─────────────────────────────────────────────────────────────────────
logs:
	docker logs connect-in-java-api -f

logs-db:
	docker logs connect-in-java-db -f

# ── Shell ────────────────────────────────────────────────────────────────────
shell:
	docker exec -it connect-in-java-api sh

# ── Frontend ─────────────────────────────────────────────────────────────────
f-install:
	cd frontend/frontend && npm install

f-run:
	cd frontend/frontend && npm run dev
