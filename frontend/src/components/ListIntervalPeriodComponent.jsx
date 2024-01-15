import React, { Component } from 'react'
import IntervalPeriodService from '../services/IntervalPeriodService'

class ListIntervalPeriodComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                intervalPeriods: []
        }
        this.addIntervalPeriod = this.addIntervalPeriod.bind(this);
        this.editIntervalPeriod = this.editIntervalPeriod.bind(this);
        this.deleteIntervalPeriod = this.deleteIntervalPeriod.bind(this);
    }

    deleteIntervalPeriod(id){
        IntervalPeriodService.deleteIntervalPeriod(id).then( res => {
            this.setState({intervalPeriods: this.state.intervalPeriods.filter(intervalPeriod => intervalPeriod.intervalPeriodId !== id)});
        });
    }
    viewIntervalPeriod(id){
        this.props.history.push(`/view-intervalPeriod/${id}`);
    }
    editIntervalPeriod(id){
        this.props.history.push(`/add-intervalPeriod/${id}`);
    }

    componentDidMount(){
        IntervalPeriodService.getIntervalPeriods().then((res) => {
            this.setState({ intervalPeriods: res.data});
        });
    }

    addIntervalPeriod(){
        this.props.history.push('/add-intervalPeriod/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">IntervalPeriod List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addIntervalPeriod}> Add IntervalPeriod</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Start </th>
                                    <th> Duration </th>
                                    <th> RandomizeStart </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.intervalPeriods.map(
                                        intervalPeriod => 
                                        <tr key = {intervalPeriod.intervalPeriodId}>
                                             <td> { intervalPeriod.start } </td>
                                             <td> { intervalPeriod.duration } </td>
                                             <td> { intervalPeriod.randomizeStart } </td>
                                             <td>
                                                 <button onClick={ () => this.editIntervalPeriod(intervalPeriod.intervalPeriodId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteIntervalPeriod(intervalPeriod.intervalPeriodId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewIntervalPeriod(intervalPeriod.intervalPeriodId)} className="btn btn-info btn-sm">View </button>
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

export default ListIntervalPeriodComponent
