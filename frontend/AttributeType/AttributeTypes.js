import React, { Component } from 'react';
import AttributeType from "./AttributeType";

class AttributeTypes extends React.Component{

    state = {
        types: []
    };

    componentDidMount(){
        fetch('/attributeTypes')
            .then(data => data.json())
            .then(types => this.setState({types: types}))
    }

    render(){
        return(
            <div className='AttributeTypes'>
                <h3>All attribute types</h3>
                {
                    this.state.types.map(param => 
                        <div>
                            <AttributeType key={param.id} attributeType={param}/>
                        </div>                   
                    )
                } 
            </div>
        );
    }
}

export default AttributeTypes;