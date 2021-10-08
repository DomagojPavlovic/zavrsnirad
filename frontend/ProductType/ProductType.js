import React, { Component } from 'react';
import AttributeType from "../AttributeType/AttributeType";

class ProductType extends Component{

    render(){
        
        const{id, name, attributeIDs } = this.props.productType;
        const{att} = this.props.attrs;
        return(
            <div>
                <b>{name}</b>
                {
                    att.map(param =>
                        <div>
                            <AttributeType key={param.id} attributeType={param}/>
                        </div>
                    )
                }
            </div>
        );
    }
}

export default ProductType;