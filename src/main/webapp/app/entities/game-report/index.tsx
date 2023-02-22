import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import GameReport from './game-report';
import GameReportDetail from './game-report-detail';
import GameReportUpdate from './game-report-update';
import GameReportDeleteDialog from './game-report-delete-dialog';

const GameReportRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<GameReport />} />
    <Route path="new" element={<GameReportUpdate />} />
    <Route path=":id">
      <Route index element={<GameReportDetail />} />
      <Route path="edit" element={<GameReportUpdate />} />
      <Route path="delete" element={<GameReportDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GameReportRoutes;
