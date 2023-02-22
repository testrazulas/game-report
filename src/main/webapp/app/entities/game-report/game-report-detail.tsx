import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './game-report.reducer';

export const GameReportDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gameReportEntity = useAppSelector(state => state.gameReport.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gameReportDetailsHeading">
          <Translate contentKey="gameReportApp.gameReport.detail.title">GameReport</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{gameReportEntity.id}</dd>
          <dt>
            <span id="pokemonName">
              <Translate contentKey="gameReportApp.gameReport.pokemonName">Pokemon Name</Translate>
            </span>
          </dt>
          <dd>{gameReportEntity.pokemonName}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="gameReportApp.gameReport.type">Type</Translate>
            </span>
          </dt>
          <dd>{gameReportEntity.type}</dd>
          <dt>
            <span id="lane">
              <Translate contentKey="gameReportApp.gameReport.lane">Lane</Translate>
            </span>
          </dt>
          <dd>{gameReportEntity.lane}</dd>
          <dt>
            <span id="gameDate">
              <Translate contentKey="gameReportApp.gameReport.gameDate">Game Date</Translate>
            </span>
          </dt>
          <dd>
            {gameReportEntity.gameDate ? <TextFormat value={gameReportEntity.gameDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="result">
              <Translate contentKey="gameReportApp.gameReport.result">Result</Translate>
            </span>
          </dt>
          <dd>{gameReportEntity.result}</dd>
          <dt>
            <span id="mvp">
              <Translate contentKey="gameReportApp.gameReport.mvp">Mvp</Translate>
            </span>
          </dt>
          <dd>{gameReportEntity.mvp}</dd>
        </dl>
        <Button tag={Link} to="/game-report" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-report/${gameReportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GameReportDetail;
