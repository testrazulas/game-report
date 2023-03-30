import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGameReport } from 'app/shared/model/game-report.model';
import { getEntities } from './game-report.reducer';
import { MdCatchingPokemon, MdOutlineCatchingPokemon } from 'react-icons/md';

export const GameReport = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const gameReportList = useAppSelector(state => state.gameReport.entities);
  const loading = useAppSelector(state => state.gameReport.loading);
  const totalItems = useAppSelector(state => state.gameReport.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="game-report-heading" data-cy="GameReportHeading" className="entities-header">
        <Translate contentKey="gameReportApp.gameReport.home.title">Game Reports</Translate>
        <MdCatchingPokemon />
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <div className="svgRefresh"></div>
          </Button>
        </div>
      </h2>
      <div className="table-responsive table-responsive-div">
        <div className="table-responsive-height-max">
          {gameReportList && gameReportList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="text-center" onClick={sort('id')}>
                    <Translate contentKey="gameReportApp.gameReport.id">ID</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="text-center" onClick={sort('pokemonName')}>
                    <Translate contentKey="gameReportApp.gameReport.pokemonName">Pokemon Name</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="text-center" onClick={sort('type')}>
                    <Translate contentKey="gameReportApp.gameReport.type">Type</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="text-center" onClick={sort('lane')}>
                    <Translate contentKey="gameReportApp.gameReport.lane">Lane</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="text-center" onClick={sort('gameDate')}>
                    <Translate contentKey="gameReportApp.gameReport.gameDate">Game Date</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="text-center" onClick={sort('result')}>
                    <Translate contentKey="gameReportApp.gameReport.result">Result</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="text-center" onClick={sort('mvp')}>
                    <Translate contentKey="gameReportApp.gameReport.mvp">Mvp</Translate> <MdOutlineCatchingPokemon />
                  </th>
                  <th className="hand text-center">Actions</th>
                </tr>
              </thead>
              <tbody>
                {gameReportList.map((gameReport, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td className="text-center">{gameReport.id}</td>
                    <td className="text-center">{gameReport.pokemonName}</td>
                    <td className="text-center">{gameReport.type}</td>
                    <td className="text-center">{gameReport.lane}</td>
                    <td className="text-center">
                      {gameReport.gameDate ? <TextFormat type="date" value={gameReport.gameDate} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td className="text-center">
                      <Translate contentKey={`gameReportApp.resultGame.${gameReport.result}`} />
                    </td>
                    <td className="text-center">
                      <Translate contentKey={`gameReportApp.mvpResult.${gameReport.mvp}`} />
                    </td>
                    <td className="text-center">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/game-report/${gameReport.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <div title="View GameReport" className="svgView"></div>
                          <span className="d-none d-md-inline"></span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/game-report/${gameReport.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <div title="Edit GameReport" className="svgEdit"></div>
                          <span className="d-none d-md-inline"></span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/game-report/${gameReport.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <div title="Delete GameReport" className="svgDelete"></div>
                          <span className="d-none d-md-inline"></span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="gameReportApp.gameReport.home.notFound">No Game Reports found</Translate>
              </div>
            )
          )}
        </div>
      </div>
      {totalItems ? (
        <div className={gameReportList && gameReportList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
      <div className="svgPokemon">
        <Link to="/game-report/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
          &nbsp;
          <div className="svgPlus"></div>
          <Translate contentKey="gameReportApp.gameReport.home.createLabel">new Game Report</Translate>
        </Link>
      </div>
      <div className="hipster"></div>
    </div>
  );
};

export default GameReport;
