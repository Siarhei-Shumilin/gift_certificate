import React, {Component} from 'react';
import {Card, Form, Button, Col} from'react-bootstrap';

export default class GiftCertificate extends Component{

constructor(props){
    super(props);
    this.state = {name:'', description:''}
    this.certificateChange=this.certificateChange.bind(this);
    this.submitCertificate=this.submitCertificate.bind(this);
}

submitCertificate(event){
    alert("Name: " + this.state.name + ", Description: " + this.state.description);
    event.preventDefault();
}

certificateChange(event){
    this.setState({
        [event.target.name]:event.target.value
    });
}

    render() {
        return(
            <Card className={"border border-dark bg-dark text-white"}>
                <Card.Header>Add Cerificate</Card.Header>
                <Form onSubmit={this.submitCertificate} id="certificateFormId">
                <Card.Body>
                   
                       <Form.Group as={Col} controlId="formGridName">
                           <Form.Label>Name</Form.Label>
                           <Form.Control required
                           className={"bg-dark text-white"}
                           type="text" name="name"
                           value = {this.state.name}
                           onChange = {this.certificateChange} 
                           placeholder="Enter certificate name" />
                       </Form.Group>

                       <Form.Group as={Col} controlId="formGridDescription">
                           <Form.Label>Description</Form.Label>
                           <Form.Control required
                           className={"bg-dark text-white"}
                           type="text" name = "description"
                           value = {this.state.description}
                           onChange = {this.certificateChange} 
                           placeholder="Enter certificate description" />
                       </Form.Group>      
                    
                </Card.Body>
                
                <Card.Footer style={{"textAlign":"right"}}>
                      <Button size="sm" variant="success" type="submit">Save</Button>
                </Card.Footer>
                </Form> 
                </Card>
        )
    }
}