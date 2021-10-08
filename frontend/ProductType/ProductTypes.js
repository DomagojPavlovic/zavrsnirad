import React, { Component } from 'react';
import AttributeTypeProxy from './AttributeTypeProxy';


class ProductTypes extends React.Component{

    state = {
        types: []
    };

    componentDidMount(){
        fetch('/productTypes')
            .then(data => data.json())
            .then(types => this.setState({types: types}))
    }
    
    render(){
        return(
            <div>
                <h3>ProductTypes</h3>
                {
                    this.state.types.map(param => 
                        <div>
                            <b>{param.name}</b>
                            {
                                param.attributeIDs.map(singleAttributeID =>
                                    <div>
                                        <AttributeTypeProxy key={singleAttributeID}  iden={singleAttributeID}/>                                
                                    </div>
                                )
                            }
                            <br/>
                        </div>
                    )
                }
            </div>
        );
    }
}

export default ProductTypes;