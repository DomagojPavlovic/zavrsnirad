import React, { Component } from 'react';
import Queries from './Queries';

class GenerateDropdowns extends Component{

    constructor(props){
        super(props);

        this.state ={
            value: {}
        }

    
    fetch('/attributeTypes/' + this.props.id)
            .then(data => data.json())
            .then(type => this.setState({value: type}));
    }

    render(){

        let size = 0
        for (let val in this.state.value.domainValues){
            size++;
        }

        if(this.state.value.rangeAttribute || size == 1){
            return(
                <select name={this.props.id} onChange={(event)=>this.props.fnc(this.props.id, event, this.props.index)}>
                    <option value="0">none</option>
                    <option value="1">Equals</option>
                    <option value="2">Not equals</option>
                    <option value="3">Greater than</option>
                    <option value="4">Greater than or equals</option>
                    <option value="5">Lesser than</option>
                    <option value="6">Lesser than of equals</option>
                </select>
            );
        }

        return(
            <select name={this.props.id} onChange={(event)=>this.props.fnc(this.props.id, event, this.props.index)}>
                <option value="0">none</option>
                <option value="1">Equals</option>
                <option value="2">Not equals</option>
            </select>
        );

    }
}

export default GenerateDropdowns;