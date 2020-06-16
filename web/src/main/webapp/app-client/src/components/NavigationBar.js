import React, {Component} from 'react';
import {Navbar, Nav} from 'react-bootstrap';
import {Link} from 'react-router-dom';

export default class NavigationBar extends Component {
   render() {
    return(
        <Navbar bg="dark" variant="dark">
           <Navbar.Brand href="/">Gift Certificates</Navbar.Brand>
              <Nav className="mr-auto">
                  <Link to={"add"} className="nav-link">Add Certificate</Link>
                  <Link to={"list"} className="nav-link">Certificate List</Link>
              </Nav>
        </Navbar>
    )
   }
}