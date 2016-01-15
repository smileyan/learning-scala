package ch5

sealed trait PreTaxDeductions
sealed trait PostTaxDeductions
sealed trait Final

case class Employee(
  name: String,
  annualSalary: Float,
  taxRate: Float,
  insurancePremiumsPerPayPeriod: Float,
  _401kDeductionRate: Float,
  postTaxDeductions: Float)

case class Pay[Step](empoyee: Employee, netPay: Float)

object Payroll {
  def start(empoyee: Employee): Pay[PreTaxDeductions] =
    Pay[PreTaxDeductions](empoyee, empoyee.annualSalary / 26.0F)

  def minusInsurance(pay: Pay[PreTaxDeductions]): Pay[PreTaxDeductions] = {
    val newNet = pay.netPay - pay.empoyee.insurancePremiumsPerPayPeriod
    pay copy (netPay = newNet)
  }

  def minus401K(pay: Pay[PreTaxDeductions]): Pay[PreTaxDeductions] = {
    val newNet = pay.netPay - pay.empoyee._401kDeductionRate * pay.netPay
    pay copy (netPay = newNet)
  }

  def minusTax(pay: Pay[PreTaxDeductions]): Pay[PostTaxDeductions] = {
    val newNet = pay.netPay - pay.empoyee.taxRate * pay.netPay
    pay copy (netPay = newNet)
  }

  def minusFinalDeductions(pay: Pay[PostTaxDeductions]): Pay[Final] = {
    val newNet = pay.netPay - pay.empoyee.postTaxDeductions
    pay copy (netPay = newNet)
  }
}




