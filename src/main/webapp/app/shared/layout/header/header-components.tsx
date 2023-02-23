import React from 'react';
import { Translate } from 'react-jhipster';

import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { SiPokemon } from 'react-icons/si';
import { MdCatchingPokemon } from 'react-icons/md';

export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/pokemon4.png" alt="Logo" />
  </div>
);

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <BrandIcon />
    <span className="brand-title">GAME-REPORT</span>
    <span className="navbar-version">{VERSION}</span>
  </NavbarBrand>
);

export const Home = () => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const GameReport = () => (
  <NavItem>
    <NavLink tag={Link} to="/game-report" className="d-flex align-items-center">
      <SiPokemon />
      <span>
        <Translate contentKey="global.menu.entities.gameReport">GameReport</Translate>
      </span>
    </NavLink>
  </NavItem>
);

export const Player = () => (
  <NavItem>
    <NavLink tag={Link} to="/player" className="d-flex align-items-center">
      <MdCatchingPokemon />
      <span>
        <Translate contentKey="global.menu.entities.player">Player</Translate>
      </span>
    </NavLink>
  </NavItem>
);
