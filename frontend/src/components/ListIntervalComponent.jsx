import React, { Component } from 'react'
import IntervalService from '../services/IntervalService'

class ListIntervalComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                intervals: []
        }
        this.addInterval = this.addInterval.bind(this);
        this.editInterval = this.editInterval.bind(this);
        this.deleteInterval = this.deleteInterval.bind(this);
    }

    deleteInterval(id){
        IntervalService.deleteInterval(id).then( res => {
            this.setState({intervals: this.state.intervals.filter(interval => interval.intervalId !== id)});
        });
    }
    viewInterval(id){
        this.props.history.push(`/view-interval/${id}`);
    }
    editInterval(id){
        this.props.history.push(`/add-interval/${id}`);
    }

    componentDidMount(){
        IntervalService.getIntervals().then((res) => {
            this.setState({ intervals: res.data});
        });
    }

    addInterval(){
        this.props.history.push('/add-interval/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Interval List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addInterval}> Add Interval</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.intervals.map(
                                        interval => 
                                        <tr key = {interval.intervalId}>
                                             <td>
                                                 <button onClick={ () => this.editInterval(interval.intervalId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteInterval(interval.intervalId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewInterval(interval.intervalId)} className="btn btn-info btn-sm">View </button>
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

export default ListIntervalComponent
