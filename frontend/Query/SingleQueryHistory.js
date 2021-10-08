import React, { Component } from 'react';
import ProductFormProxy from '../Product/ProductFormProxy';
import ProductFormProxyDomain from '../Product/ProductFormProxyDomain';
import FilterTypeProxy from './FilterTypeProxy';

class SingleQueryHistory extends React.Component{



    render(){
        const{productTypeID, queryElements} = this.props.query;

        return(
            <div>
                <table class= "black">
                        <tr>
                            <th>
                                Attribute name
                            </th>
                            <th>
                                Attribute domain
                            </th>
                            <th>
                                Filter type
                            </th>
                            <th>
                                Filter value
                            </th>
                            <th>
                                Filter priority
                            </th>
                        </tr>
                        {
                            queryElements.map((element, index) => 
                                <tr>
                                    <td>
                                        <ProductFormProxy id={element.attributeTypeID}/>
                                    </td>
                                    <td>
                                        <ProductFormProxyDomain id={element.attributeTypeID}/>
                                    </td>
                                    <td>
                                        <FilterTypeProxy queryType={element.queryType}/>
                                    </td>
                                    <td>
                                        {element.queryValue}
                                    </td>
                                    <td>
                                        {element.priority}
                                    </td>
                                </tr>
                            )
                        }
                </table>
                
            </div>
        );

    }

}

export default SingleQueryHistory;