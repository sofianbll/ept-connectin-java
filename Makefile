.PHONY : f-install f-run down-build-up-dev

f-install:
	@@echo "Installation du Frontend..."
	@@cd frontend/frontend && npm install

f-run:
	cd  frontend/frontend && npm run dev

down-build-up-dev:
	docker compose -f docker-compose.dev.yml down && docker compose -f docker-compose.dev.yml up --build