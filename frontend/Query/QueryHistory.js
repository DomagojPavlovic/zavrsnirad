import React, { Component } from 'react';

import SingleQueryHistory from './SingleQueryHistory';

class QueryHistory extends React.Component{


    state = {
        reExecute: this.props.reExecute,
        queries: []
    }

    componentDidMount(){
        fetch('/searchQueries/history')
            .then(data => data.json())
            .then(types => this.setState({queries: types}))
    }

    render(){
        let queriesArray = []
        let index = 0;
        for(let query in this.state.queries){
            queriesArray[index] = this.state.queries[query]
            index++
        }

        return(
            <div>
                {
                    queriesArray.map((query, index)=>
                        <div>
                            <SingleQueryHistory query ={query}/>
                            <table class="submit">
                                <tr>
                                    <td>
                                        <button onClick={() => this.state.reExecute(query)}>Re-execute</button>
                                    </td>
                                </tr>
                            </table>
                            <br/>
                        </div>
                    )
                }
                
            </div>
        );
        
    }
}

export default QueryHistory;