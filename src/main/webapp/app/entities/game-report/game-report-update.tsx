import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGameReport } from 'app/shared/model/game-report.model';
import { resultGame } from 'app/shared/model/enumerations/result-game.model';
import { mvpResult } from 'app/shared/model/enumerations/mvp-result.model';
import { getEntity, updateEntity, createEntity, reset } from './game-report.reducer';

export const GameReportUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gameReportEntity = useAppSelector(state => state.gameReport.entity);
  const loading = useAppSelector(state => state.gameReport.loading);
  const updating = useAppSelector(state => state.gameReport.updating);
  const updateSuccess = useAppSelector(state => state.gameReport.updateSuccess);
  const resultGameValues = Object.keys(resultGame);
  const mvpResultValues = Object.keys(mvpResult);

  const handleClose = () => {
    navigate('/game-report' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.gameDate = convertDateTimeToServer(values.gameDate);

    const entity = {
      ...gameReportEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          gameDate: displayDefaultDateTime(),
        }
      : {
          result: 'WIN',
          mvp: 'YES',
          ...gameReportEntity,
          gameDate: convertDateTimeFromServer(gameReportEntity.gameDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gameReportApp.gameReport.home.createOrEditLabel" data-cy="GameReportCreateUpdateHeading">
            <Translate contentKey="gameReportApp.gameReport.home.createOrEditLabel">Create or edit a GameReport</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="game-report-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('gameReportApp.gameReport.pokemonName')}
                id="game-report-pokemonName"
                name="pokemonName"
                data-cy="pokemonName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gameReportApp.gameReport.type')}
                id="game-report-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gameReportApp.gameReport.lane')}
                id="game-report-lane"
                name="lane"
                data-cy="lane"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gameReportApp.gameReport.gameDate')}
                id="game-report-gameDate"
                name="gameDate"
                data-cy="gameDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('gameReportApp.gameReport.result')}
                id="game-report-result"
                name="result"
                data-cy="result"
                type="select"
              >
                {resultGameValues.map(resultGame => (
                  <option value={resultGame} key={resultGame}>
                    {translate('gameReportApp.resultGame.' + resultGame)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('gameReportApp.gameReport.mvp')} id="game-report-mvp" name="mvp" data-cy="mvp" type="select">
                {mvpResultValues.map(mvpResult => (
                  <option value={mvpResult} key={mvpResult}>
                    {translate('gameReportApp.mvpResult.' + mvpResult)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/game-report" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default GameReportUpdate;
