import React, { Component } from 'react';
import ProductType from './ProductType';
import AttributeType from '../AttributeType/AttributeType';

class AttributeTypeProxy extends Component{

    constructor(props){
        super(props);

        this.state = {
            value:{}
        }

        fetch('/attributeTypes/' + this.props.iden)
            .then(data => data.json())
            .then(type => this.setState({value: type}));
    }

    render(){
        return(
            <div>
                {                
                    <AttributeType  attributeType={this.state.value}/>
                }
            </div>
        );
    }
}

export default AttributeTypeProxy;