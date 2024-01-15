import React, { Component } from 'react'
import ValuesMapService from '../services/ValuesMapService'

class ListValuesMapComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                valuesMaps: []
        }
        this.addValuesMap = this.addValuesMap.bind(this);
        this.editValuesMap = this.editValuesMap.bind(this);
        this.deleteValuesMap = this.deleteValuesMap.bind(this);
    }

    deleteValuesMap(id){
        ValuesMapService.deleteValuesMap(id).then( res => {
            this.setState({valuesMaps: this.state.valuesMaps.filter(valuesMap => valuesMap.valuesMapId !== id)});
        });
    }
    viewValuesMap(id){
        this.props.history.push(`/view-valuesMap/${id}`);
    }
    editValuesMap(id){
        this.props.history.push(`/add-valuesMap/${id}`);
    }

    componentDidMount(){
        ValuesMapService.getValuesMaps().then((res) => {
            this.setState({ valuesMaps: res.data});
        });
    }

    addValuesMap(){
        this.props.history.push('/add-valuesMap/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">ValuesMap List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addValuesMap}> Add ValuesMap</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Type </th>
                                    <th> Values </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.valuesMaps.map(
                                        valuesMap => 
                                        <tr key = {valuesMap.valuesMapId}>
                                             <td> { valuesMap.type } </td>
                                             <td> { valuesMap.values } </td>
                                             <td>
                                                 <button onClick={ () => this.editValuesMap(valuesMap.valuesMapId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteValuesMap(valuesMap.valuesMapId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewValuesMap(valuesMap.valuesMapId)} className="btn btn-info btn-sm">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListValuesMapComponent
