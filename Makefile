.PHONY : f-install f-run

f-install:
	@@echo "Installation du Frontend..."
	@@cd frontend/frontend && npm install

f-run:
	cd  frontend/frontend && npm run dev