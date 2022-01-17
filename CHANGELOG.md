# Semantic Versioning Changelog

## [1.12.2](https://github.com/JayDamon/transaction-service/compare/v1.12.1...v1.12.2) (2022-01-17)


### Bug Fixes

* **app:** fix test ([3d35856](https://github.com/JayDamon/transaction-service/commit/3d35856a96ff1407f888af9e5a3e355e8171b232))

## [1.12.1](https://github.com/JayDamon/transaction-service/compare/v1.12.0...v1.12.1) (2022-01-16)


### Bug Fixes

* **docker:** Add back incorrect line deletion ([159f240](https://github.com/JayDamon/transaction-service/commit/159f240b5bd5eaab6c682c3215e8cc2f939a20d3))

# [1.12.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.11.0...v1.12.0) (2021-11-13)


### Features

* **release:** Move docker build and push out of semantic ([60de4f9](https://github.com/JayDamon/oaka-transaction-service/commit/60de4f94980adaeec496dedddb30880cec4ecf68))

# [1.11.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.10.0...v1.11.0) (2021-11-13)


### Features

* **semantic:** Update semantic release order ([d268d5a](https://github.com/JayDamon/oaka-transaction-service/commit/d268d5a238803edd5172c205bff4d590ec30af11))

# [1.10.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.9.0...v1.10.0) (2021-11-13)


### Features

* **docker:** Upgrade base image to jdk 17 ([4f0eed6](https://github.com/JayDamon/oaka-transaction-service/commit/4f0eed624f48063300326af9251ec976bc834d72))

# [1.9.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.8.0...v1.9.0) (2021-11-13)


### Features

* **jdk:** Java 17 upgrade ([5ed50af](https://github.com/JayDamon/oaka-transaction-service/commit/5ed50af670d7d287035a62fd470a41e851451e82))

# [1.8.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.7.0...v1.8.0) (2021-08-26)


### Features

* **rabbit:** Add rabbit mq messaging queue for async communication ([ab97045](https://github.com/JayDamon/oaka-transaction-service/commit/ab97045e9fb6b5eef6d9ea3401360bebd75c6168))

# [1.7.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.6.4...v1.7.0) (2021-08-13)


### Features

* **transaction:** Add update transaction endpoint ([8181955](https://github.com/JayDamon/oaka-transaction-service/commit/81819554ccabbc156b030e7de17dd7cc2232ab61))

## [1.6.4](https://github.com/JayDamon/oaka-transaction-service/compare/v1.6.3...v1.6.4) (2021-08-13)


### Bug Fixes

* **kubernetes:** Use fabric8 client to avoid log error" ([7f06e88](https://github.com/JayDamon/oaka-transaction-service/commit/7f06e88b781dd3a78206e8e7c0388ad1cf95112b))

## [1.6.3](https://github.com/JayDamon/oaka-transaction-service/compare/v1.6.2...v1.6.3) (2021-08-13)


### Bug Fixes

* **service:** Still return transactions that have no account ([b0d381f](https://github.com/JayDamon/oaka-transaction-service/commit/b0d381ff7c44d68916c868bf4b7821efb2f127a6))

## [1.6.2](https://github.com/JayDamon/oaka-transaction-service/compare/v1.6.1...v1.6.2) (2021-08-11)


### Bug Fixes

* **budget:** Use budget type over budget id ([715bea5](https://github.com/JayDamon/oaka-transaction-service/commit/715bea51781573081cf5e048bdf484403cec40ba))

## [1.6.1](https://github.com/JayDamon/oaka-transaction-service/compare/v1.6.0...v1.6.1) (2021-08-10)


### Bug Fixes

* **transaction:** Return bad request when transaction info missing ([77dea93](https://github.com/JayDamon/oaka-transaction-service/commit/77dea9369168d1e08866f7d051f38dd8c607d5ba)), closes [#32](https://github.com/JayDamon/oaka-transaction-service/issues/32)

# [1.6.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.5.0...v1.6.0) (2021-08-08)


### Features

* **security:** Add discriminator based multi tenancy ([a771443](https://github.com/JayDamon/oaka-transaction-service/commit/a7714438500c255bc5e39e9d305b214f4bd88461))

# [1.5.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.4.0...v1.5.0) (2021-08-06)


### Bug Fixes

* **ci:** Fix main branch only exclusion ([393f4f9](https://github.com/JayDamon/oaka-transaction-service/commit/393f4f9b3087bc401d4e59053a8bf70deb62bb17))


### Features

* **ci:** limit to only main branch for circle CI ([4c6867a](https://github.com/JayDamon/oaka-transaction-service/commit/4c6867a6825fb04e6321f4d28be7d7410a986fc4))

# [1.4.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.3.2...v1.4.0) (2021-08-05)


### Features

* **security:** Integrate local with oauth2/keycloak ([4b033ba](https://github.com/JayDamon/oaka-transaction-service/commit/4b033bae4f9c8596dafc3be98565c77ff680af40))

## [1.3.2](https://github.com/JayDamon/oaka-transaction-service/compare/v1.3.1...v1.3.2) (2021-07-27)


### Bug Fixes

* Update h2 database ([1455984](https://github.com/JayDamon/oaka-transaction-service/commit/14559847c2d342adaee553ef80d2450cd26dfc7a))

## [1.3.1](https://github.com/JayDamon/oaka-transaction-service/compare/v1.3.0...v1.3.1) (2021-07-27)


### Bug Fixes

* move web client config ([e678d57](https://github.com/JayDamon/oaka-transaction-service/commit/e678d57715f4135bcd8c76fe2ded14afa54d3ff5))

# [1.3.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.2.5...v1.3.0) (2021-07-27)


### Features

* **client:** Add local docker config ([0fd906f](https://github.com/JayDamon/oaka-transaction-service/commit/0fd906f165fe1ada6b6422342f9cc7160b3605f9))

## [1.2.5](https://github.com/JayDamon/oaka-transaction-service/compare/v1.2.4...v1.2.5) (2021-07-26)


### Bug Fixes

* **client:** Remove Ports ([20c4887](https://github.com/JayDamon/oaka-transaction-service/commit/20c48876d4b5b3a3055cf7da90e0a995ebd303b4))

## [1.2.4](https://github.com/JayDamon/oaka-transaction-service/compare/v1.2.3...v1.2.4) (2021-07-25)


### Bug Fixes

* **client:** Set port for http requests ([d9a2aa2](https://github.com/JayDamon/oaka-transaction-service/commit/d9a2aa2d6e4fdf37b5a13572e136e16439e11e35))

## [1.2.3](https://github.com/JayDamon/oaka-transaction-service/compare/v1.2.2...v1.2.3) (2021-07-25)


### Bug Fixes

* **flyway:** Properly setup flyway ([6a7e371](https://github.com/JayDamon/oaka-transaction-service/commit/6a7e37176deebee4606474729e1e79118a082a78))

## [1.2.2](https://github.com/JayDamon/oaka-transaction-service/compare/v1.2.1...v1.2.2) (2021-07-25)


### Bug Fixes

* **discovery:** Remove load balancer for web client ([80f4e6a](https://github.com/JayDamon/oaka-transaction-service/commit/80f4e6a64d8f5db2acfd76e76b5c0e1191f47630))

## [1.2.1](https://github.com/JayDamon/oaka-transaction-service/compare/v1.2.0...v1.2.1) (2021-07-25)


### Bug Fixes

* **discovery:** Use generic discovery client ([3bbbaf7](https://github.com/JayDamon/oaka-transaction-service/commit/3bbbaf74898c8943a2e440ee0629ceb8f0b235b8))

# [1.2.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.1.0...v1.2.0) (2021-07-25)


### Features

* **api:** Update api to work with k8s gateway ([298ca5c](https://github.com/JayDamon/oaka-transaction-service/commit/298ca5c10d9563dd7f07ccf6273b474652078735))

# [1.1.0](https://github.com/JayDamon/oaka-transaction-service/compare/v1.0.1...v1.1.0) (2021-07-25)


### Features

* **db:** Add flyway ([23a9195](https://github.com/JayDamon/oaka-transaction-service/commit/23a9195fff95558b3e1942c4886b6e3172002ca1))

## [1.0.1](https://github.com/JayDamon/oaka-transaction-service/compare/v1.0.0...v1.0.1) (2021-07-21)


### Bug Fixes

* **versioning:** change repo name in .releaserc ([baa4c40](https://github.com/JayDamon/oaka-transaction-service/commit/baa4c4093ec3abcbdff3c849753be92a27b5084e))

# 1.0.0 (2021-07-21)


### Bug Fixes

* **maven:** take ownership of gradlew and remove commented code ([e91c5dc](https://github.com/JayDamon/oaka-transaction-service/commit/e91c5dc9eb249597e22963885c70187fffb350d5))


### Features

* **config:** Add circleci, semantic-release, kubernetes ([995bc4a](https://github.com/JayDamon/oaka-transaction-service/commit/995bc4aad6f25d7b61d124e758995a0fadce4ad8))
