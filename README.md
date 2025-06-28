# Machine Learning with Scala and Spark

This project is a collection of machine learning algorithms implemented in Scala and Java, primarily using Apache Spark for distributed computing. It demonstrates how to build and apply ML models on data loaded from MongoDB.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Available Algorithms](#available-algorithms)

## Features

- **Data Loading**: Loads data from MongoDB using the official Spark connector.
- **Data Preprocessing**: Converts raw data into a sparse matrix format suitable for machine learning models.
- **Machine Learning Algorithms**: Implements several common ML algorithms, including:
  - Dimensionality Reduction (PCA)
  - Clustering (K-Means)
  - Locality Sensitive Hashing (LSH)

## Technologies Used

- **Languages**: Scala, Java
- **Frameworks & Libraries**:
  - **Apache Spark 2.1.1**:
    - Spark Core
    - Spark MLlib
    - Spark SQL
    - Spark Streaming
  - **MongoDB Spark Connector**: For reading data from MongoDB.
  - **Smile**: A fast and comprehensive machine learning engine.
  - **ELKI**: A data mining software framework.
- **Build Tool**: Apache Maven

## Project Structure

```
MLScala/
├── pom.xml                 # Maven build configuration
├── src/
│   ├── main/
│   │   ├── java/           # Java source files
│   │   │   ├── common/
│   │   │   ├── services/
│   │   └── scala/          # Scala source files
│   │       ├── MLAlgos/      # Machine learning algorithms
│   │       ├── common/
│   │       └── LoadData.scala # Main application entry point
│   └── test/
└── README.md
```

## Getting Started

### Prerequisites

- Java 8
- Apache Maven
- Apache Spark 2.1.1
- Access to a MongoDB instance

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/MLScala.git
    cd MLScala
    ```

2.  **Install dependencies:**
    ```bash
    mvn clean install
    ```

## Usage

The main entry point of the application is `LoadData.scala`. You can run it using `spark-submit`.

1.  **Package the application:**
    ```bash
    mvn package
    ```

2.  **Run with `spark-submit`:**
    ```bash
    spark-submit \
      --class LoadData \
      --master <your-spark-master> \
      target/MLScala-1.0-SNAPSHOT.jar
    ```
    Make sure to configure the MongoDB connection details in the `Config.properties` file or pass them as command-line arguments as required by your implementation.

## Available Algorithms

The following machine learning algorithms are implemented in this project:

-   **Dimensionality Reduction**:
    -   **Principal Component Analysis (PCA)**: Implemented using Spark's MLlib. See `DimensionalityReduction.scala`.
-   **Clustering**:
    -   **K-Means**: Implemented using both Spark's MLlib and the Smile library. See `ClusteringAlgos.scala`.
-   **Locality Sensitive Hashing (LSH)**:
    -   **MrSqueeze LSH**: An implementation of LSH for finding similar items. See `LSHAlgos.scala`.

You can enable or disable the execution of these algorithms by modifying the `newStart` method in `LoadData.scala`.