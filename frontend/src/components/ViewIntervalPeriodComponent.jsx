import React, { Component } from 'react'
import IntervalPeriodService from '../services/IntervalPeriodService'

class ViewIntervalPeriodComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            intervalPeriod: {}
        }
    }

    componentDidMount(){
        IntervalPeriodService.getIntervalPeriodById(this.state.id).then( res => {
            this.setState({intervalPeriod: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View IntervalPeriod Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> start:&emsp; </label>
                            <div> { this.state.intervalPeriod.start }</div>
                        </div>
                        <div className = "row">
                            <label> duration:&emsp; </label>
                            <div> { this.state.intervalPeriod.duration }</div>
                        </div>
                        <div className = "row">
                            <label> randomizeStart:&emsp; </label>
                            <div> { this.state.intervalPeriod.randomizeStart }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewIntervalPeriodComponent
