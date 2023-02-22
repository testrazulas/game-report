import dayjs from 'dayjs';
import { resultGame } from 'app/shared/model/enumerations/result-game.model';
import { mvpResult } from 'app/shared/model/enumerations/mvp-result.model';

export interface IGameReport {
  id?: number;
  pokemonName?: string;
  type?: string;
  lane?: string;
  gameDate?: string;
  result?: resultGame | null;
  mvp?: mvpResult | null;
}

export const defaultValue: Readonly<IGameReport> = {};
