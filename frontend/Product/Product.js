import React, { Component } from 'react';

import ProductFormProxy from "./ProductFormProxy";

class Product extends React.Component{


    render(){

        const{productID, productTypeID, name, description, attributeValues} = this.props.product;
        
        let array = [];
        for(let a in attributeValues){
            array.push(attributeValues[a]);
        }

        return(
            <div>
                <table class="black">
                    <tr>
                        <td>
                            ATTRIBUTE_NAME
                        </td>
                        <td>
                            ATTRIBUTE_VALUE
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Product name
                        </td>
                        <td>
                            {name}
                        </td>
                    </tr>
                    {
                        array.map(param =>
                            <tr>
                                <td>
                                    <ProductFormProxy id={param.id}/>
                                </td>
                                <td>
                                    {param.value}
                                </td>
                            </tr>
                        )
                    }
                </table>
               
            </div>
        )
    }
}

export default Product;