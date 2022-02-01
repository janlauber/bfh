![Logo](img/ActoreRaceLogo.png)
# Blockweek 2 - Group java-10

## Documentation

**[docs](doc)**

## GitFlow

| **Ref-Flow**    | **CI/CD-Stage**       | **Description**  |
| :-------------- | :--------------- | :--------------- |
| `git push` on `feature-branch` | `test` & `build` | Every commit/push on a feature branch triggers a pipeline with `mvn verify` |
| `feature-branch` -> `main` | `test`, `build` & `release` | A merge on the `main` branch triggers the creation of a docker container |

## docker-container

```bash
docker run registry.gitlab.ti.bfh.ch/blockweek2/2021/actor-race-java-10:latest
```
