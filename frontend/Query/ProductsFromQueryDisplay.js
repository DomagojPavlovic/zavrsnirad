import React, { Component } from 'react';
import ProductFormProxy from '../Product/ProductFormProxy';


class ProductsFromQueryDisplay extends Component{
    render(){

        const{values} = this.props.product

        let size = 0;
        let headers = [];
        for(let val in values){
            headers = values[val].attributeValues;
            size++
            break;
        }

        let array = [];
        for(let val in headers){
            array.push(headers[val])
        }

        let array2 = [];
        for(let val in values){
            array2.push(values[val]);
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
export default ProductsFromQueryDisplay;