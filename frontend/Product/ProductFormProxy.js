import React, { Component } from 'react';

class ProductFormProxy extends Component{

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
        return(
            <div>
                    {this.state.value.name}
            </div>
        );
    }
}

export default ProductFormProxy;