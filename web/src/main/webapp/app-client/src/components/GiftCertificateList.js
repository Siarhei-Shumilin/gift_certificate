import React, {Component} from 'react';
import {Table, ButtonGroup, Button, Card, InputGroup, FormControl} from'react-bootstrap';
import axios from 'axios';
export default class GiftCertificateList extends Component{

    constructor(props){
        super(props);
        this.state = {
            certificates: [],
            currentPage : 1,
            certificatesPerPage : 3
        };
    }

    componentDidMount(){
        axios.get("http://localhost:8080/certificates?page=2")
        .then(response => response.data)
        .then((data) => {
        this.setState({certificates:data});
        });
    }
  
    changePage = event => {
        this.setState({
            [event.target.name]: parseInt(event.target.value)
        });

    };

    firstPage = () => {
       if(this.state.currentPage > 1) {
           this.setState({
               currentPage: 1
           });
       }
    };

    prevPage = () => {
        if(this.state.currentPage > 1) {
            this.setState({
                currentPage: this.state.currentPage - 1
            });
        }
    };

    lastPage = () => {
        if(this.state.currentPage < Math.ceil(this.state.certificates.length / this.state.certificatesPerPage)) {
            this.setState({
                currentPage: Math.ceil(this.state.certificates.length / this.state.certificatesPerPage)
            });
        }
    };

    nextPage = () => {
        if(this.state.currentPage < Math.ceil(this.state.certificates.length / this.state.certificatesPerPage)) {
            this.setState({
                currentPage: this.state.currentPage + 1
            });
        }
    };


    render() {

        const {certificates, currentPage, certificatesPerPage} = this.state;
        const lastIndex = currentPage * certificatesPerPage;
        const firstIndex = lastIndex - certificatesPerPage;
        const currentCertificates = certificates.slice(firstIndex, lastIndex);
        const totalPages = certificates.length / certificatesPerPage;

        const pageNumCss = {
            width: "45px",
            border: "1px solid #17A2B8",
            color: "#17A2B8",
            textAlign: "center",
            fontWeight: "bold"
        };

        return(
            <Card>
                    <Table bordered hover striped variant="light">
                    <thead>
                       <tr>
                          <th>Datetime</th>
                          <th>Title</th>
                          <th>Tags</th>
                          <th>Description</th>
                          <th>Price</th>
                          <th>Action</th>
                       </tr>
                    </thead>
                    <tbody>
                        {
                            certificates.length === 0 ?
                           <tr align="center">
                             <td colSpan="6">Certificate Available</td>
                           </tr> :
                           currentCertificates.map((certificate) => (
                           <tr key={certificate.id}>
                               <td>{certificate.lastUpdateDate}</td>
                               <td>{certificate.name}</td>
                               <td>{certificate.tagList.map(tag => <div>{tag.name}</div>)}</td>
                               <td>{certificate.description}</td>
                               <td>{certificate.price}</td>
                               <td>
                                   <ButtonGroup>
                                        <Button size="sm" variant="primary">View</Button>
                                        <Button size="sm" variant="warning">Edit</Button>
                                        <Button size="sm" variant="danger">Delete</Button>
                                   </ButtonGroup>
                               </td>
                           </tr>
                           ))
                        }
                    </tbody>
                    </Table>
                    <Card.Footer>
                        <div style={{"float":"left"}}>
                            Showing page {currentPage} of {totalPages}
                        </div>
                        <div style={{"float":"right"}}>
                            <InputGroup size="sm">
                            <InputGroup.Prepend>
                            <Button type="button" variant="info" disabled = {currentPage === 1 ? true:false}
                            onClick={this.firstPage}
                            >
                                First
                            </Button>
                            <Button type="button" variant="info" disabled = {currentPage === 1 ? true:false}
                            onClick={this.prevPage}
                            >
                                Prev
                            </Button>
                            </InputGroup.Prepend>
                            <FormControl style={pageNumCss} name="currentPage" value={currentPage} 
                            onChange={this.changePage}/>
                            <InputGroup.Append>
                            <Button type="button" variant="info" disabled = {currentPage === totalPages ? true:false}
                            onClick={this.nextPage}
                            >
                                Next
                            </Button>
                            <Button type="button" variant="info" disabled = {currentPage === totalPages ? true:false}
                            onClick={this.lastPage}>
                                Last
                            </Button>
                            </InputGroup.Append>
                            </InputGroup>
                        </div>
                    </Card.Footer>
                    </Card>
        )
    }
}