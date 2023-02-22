import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('GameReport e2e test', () => {
  const gameReportPageUrl = '/game-report';
  const gameReportPageUrlPattern = new RegExp('/game-report(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const gameReportSample = {
    pokemonName: 'empower Steel Chicken',
    type: 'Israel Cotton Netherlands',
    lane: 'transmitter blue',
    gameDate: '2023-02-22T10:38:21.396Z',
  };

  let gameReport;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/game-reports+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/game-reports').as('postEntityRequest');
    cy.intercept('DELETE', '/api/game-reports/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (gameReport) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/game-reports/${gameReport.id}`,
      }).then(() => {
        gameReport = undefined;
      });
    }
  });

  it('GameReports menu should load GameReports page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('game-report');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('GameReport').should('exist');
    cy.url().should('match', gameReportPageUrlPattern);
  });

  describe('GameReport page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(gameReportPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create GameReport page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/game-report/new$'));
        cy.getEntityCreateUpdateHeading('GameReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameReportPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/game-reports',
          body: gameReportSample,
        }).then(({ body }) => {
          gameReport = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/game-reports+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/game-reports?page=0&size=20>; rel="last",<http://localhost/api/game-reports?page=0&size=20>; rel="first"',
              },
              body: [gameReport],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(gameReportPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details GameReport page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('gameReport');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameReportPageUrlPattern);
      });

      it('edit button click should load edit GameReport page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('GameReport');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameReportPageUrlPattern);
      });

      it('edit button click should load edit GameReport page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('GameReport');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameReportPageUrlPattern);
      });

      it('last delete button click should delete instance of GameReport', () => {
        cy.intercept('GET', '/api/game-reports/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('gameReport').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', gameReportPageUrlPattern);

        gameReport = undefined;
      });
    });
  });

  describe('new GameReport page', () => {
    beforeEach(() => {
      cy.visit(`${gameReportPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('GameReport');
    });

    it('should create an instance of GameReport', () => {
      cy.get(`[data-cy="pokemonName"]`).type('Automotive Shores Somoni').should('have.value', 'Automotive Shores Somoni');

      cy.get(`[data-cy="type"]`).type('Dynamic Wooden').should('have.value', 'Dynamic Wooden');

      cy.get(`[data-cy="lane"]`).type('Indonesia deposit').should('have.value', 'Indonesia deposit');

      cy.get(`[data-cy="gameDate"]`).type('2023-02-21T18:02').blur().should('have.value', '2023-02-21T18:02');

      cy.get(`[data-cy="result"]`).select('WIN');

      cy.get(`[data-cy="mvp"]`).select('NO');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        gameReport = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', gameReportPageUrlPattern);
    });
  });
});
