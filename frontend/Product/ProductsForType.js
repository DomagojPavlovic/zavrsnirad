import React, { Component } from 'react';
import ProductFormProxy from './ProductFormProxy';

class ProductsForType extends React.Component{
    
    constructor(props){
        super(props);

        this.state = {
            products: []
        }

        fetch('/products/' + this.props.id)
            .then(data => data.json())
            .then(type => this.setState({products: type}));

    }

    render(){

        let size = 0;
        let headers = [];
        for(let val in this.state.products){
            headers = this.state.products[val].attributeValues;
            size++
            break;
        }

        let array = [];
        for(let val in headers){
            array.push(headers[val])
        }

        let array2 = [];
        for(let val in this.state.products){
            array2.push(this.state.products[val]);
        }

        if(size != 0){
        return(
            <div>
                <table class = "black">
                    <tr>
                        <th>
                            Name
                        </th>
                        {
                            array.map(attr =>
                                <th>
                                    <ProductFormProxy id={attr.id}/>
                                </th>
                            )
                        }
                    </tr>
                    {
                        array2.map(product => 
                            <tr>
                                <td>
                                    {product.name}
                                </td>
                                {
                                    product.attributeValues.map(attr => 
                                        <td>
                                            {attr.value}
                                        </td>
                                    )
                                }
                            </tr>    
                        )
                    }
                </table>

            </div>
        )
        }

        return(
            <div>
                There are no products of this type yet.
            </div>
        );
    }


}
export default ProductsForType;