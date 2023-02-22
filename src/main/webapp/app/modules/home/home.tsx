import './home.scss';

import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const navigate = useNavigate();
  const account = useAppSelector(state => state.authentication.account);

  useEffect(() => {
    if (account.login) {
      navigate('/game-report');
    }
  }, [account]);

  return (
    <Row>
      {account?.login ? (
        <div></div>
      ) : (
        <>
          <Col md="3" className="pad">
            <span className="hipster rounded" />
          </Col>
          <Col md="9">
            <h2>
              <Translate contentKey="home.title">Welcome to</Translate>
            </h2>
            <b className="siraTitle">GAME-POKEMON</b>
            <p className="lead"></p>
            <div>
              <Alert color="warning">
                <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

                <Link to="/login" className="alert-link">
                  <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
                </Link>
                <Translate contentKey="global.messages.info.authenticated.suffix">
                  , you can try the default accounts:
                  <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                  <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
                </Translate>
              </Alert>

              <Alert color="warning">
                <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
                <Link to="/account/register" className="alert-link">
                  <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
                </Link>
              </Alert>
            </div>
          </Col>
        </>
      )}
    </Row>
  );
};

export default Home;
