# About
<p align="justify">
PDF Pinpoint is a small-scale and simple local chatbot tool powered by retrieval-augmented generation (RAG). It leverages large language models to process and train on multiple PDF documents, enabling users to efficiently find the answers they're looking for across various PDF documents. </p>

# Getting Started

### Prerequisites 
* Ensure you have Git installed.
* Ensure you have Java 21 or later installed.
* Install Ollama and a Llama 3.2 or 3.1 model, and Nomic Embedding Model.

### Quickstart 
1. Git clone this repository.
2. Ensure you have a Llama 3.2 or 3.1 model, and Nomic Embedding Model installed.
3. Ensure within your application.properties file that you have the following llama model and embedding model listed.
  * Default Options:
     * ```spring.ai.ollama.chat.options.model=llama3.2```
     * ```spring.ai.ollama.embedding.enabled=true```
     * ```spring.ai.ollama.embedding.options.model= nomic-embed-text``` 
4. If you're looking to upload large PDF documents, within your application.properties modify these options:
   * ```spring.servlet.multipart.max-file-size=100MB```
   * ```spring.servlet.multipart.max-request-size=100MB```
   * By default they're set to 100MB.
5. All documents once uploaded are loaded into the following directory src/main/resources/pdf.
6. Start your application and go to localhost:8080 with a web browser.

# Usage
PDF Pinpoint allows you to do the following:
* Upload and manage multiple PDF documents with ease.
* Search for answers to specific questions by extracting relevant content directly from your uploaded documents.
* Identify, compare, and analyze relationships between the documents to uncover possible patterns and connections.
* Enhance productivity for academic, legal, or business purposes with PDF Pinpoint.
* Does not require an internet connection to run this application, which respects user privacy. 

# Media 
![Image](https://github.com/dug22/PDF-Pinpoint/blob/master/images/pdf%20pinpoint%20visual.png?raw=true)


# URL Endpoints:
1. **localhost:8080** accesses the main RAG chatbot application.
2. **localhost:8080/upload** endpoint to upload documents. 
3. **localhost:8080/documents** accesses a list of documents loaded into the application.
4. **localhost:8080/delete/file_name** deletes a file from the given repository.

# Contributions
If you'd like to contribute to this repository, feel free to open a pull request with your suggestions, bug fixes, or enhancements. Contributions are always welcome!


   
