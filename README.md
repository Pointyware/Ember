# Artes
Skillful Helpers

Related: https://github.com/Pointyware/AI-Licensing



```mermaid

graph
    subgraph apps
    :app-android --> :app-shared
    :app-desktop --> :app-shared
    end
    
    subgraph core
    :app-shared --> :core-common
    :app-shared --> :core-data
    :app-shared --> :core-ui
    end
    
```
