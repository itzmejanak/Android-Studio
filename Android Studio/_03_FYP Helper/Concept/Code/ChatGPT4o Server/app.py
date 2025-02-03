from fastapi import FastAPI, Query, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from urllib.parse import quote
from contextlib import asynccontextmanager
import json

@asynccontextmanager
async def lifespan(app: FastAPI):
    app.state.driver = webdriver.Chrome(options=chrome_options())
    yield
    app.state.driver.quit()

app = FastAPI(lifespan=lifespan)

# Add CORS middleware
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allow all origins for development
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

def chrome_options():
    options = Options()
    options.add_argument("--headless=new")
    options.add_argument("--disable-gpu")
    options.add_argument("--no-sandbox")
    options.add_argument("--disable-dev-shm-usage")
    options.add_argument("--ignore-certificate-errors")
    return options

@app.get("/chat")
async def chat(
    query: str = Query(..., min_length=1)
):
    try:
        driver = app.state.driver
        encoded_query = quote(query)
        driver.get(f"http://myweb.yzz.me/ChatGPT4o.php?text={encoded_query}&i=1")
        
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.TAG_NAME, 'body'))
        )
        
        raw_response = driver.find_element(By.TAG_NAME, 'body').text.strip()
        
        # Clean and format the response
        try:
            # If response is JSON-like string, parse it properly
            parsed = json.loads(raw_response)
            return {"response": parsed}
        except json.JSONDecodeError:
            # If it's plain text, return as data field
            return {"response": {"data": raw_response}}
            
    except Exception as e:
        raise HTTPException(500, detail=str(e))