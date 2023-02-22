import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './player.reducer';

export const PlayerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const playerEntity = useAppSelector(state => state.player.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="playerDetailsHeading">
          <Translate contentKey="gameReportApp.player.detail.title">Player</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{playerEntity.id}</dd>
          <dt>
            <span id="playerName">
              <Translate contentKey="gameReportApp.player.playerName">Player Name</Translate>
            </span>
          </dt>
          <dd>{playerEntity.playerName}</dd>
          <dt>
            <span id="rankPlayer">
              <Translate contentKey="gameReportApp.player.rankPlayer">Rank Player</Translate>
            </span>
          </dt>
          <dd>{playerEntity.rankPlayer}</dd>
        </dl>
        <Button tag={Link} to="/player" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/player/${playerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlayerDetail;
