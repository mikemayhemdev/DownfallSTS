package theHexaghost.cards;

import basemod.patches.com.megacrit.cardcrawl.powers.FixVigorReduction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.apache.commons.codec.binary.Hex;
import theHexaghost.HexaMod;
import theHexaghost.patches.ExhaustCardTickPatch;

public class GhostLash extends AbstractHexaCard {

    public final static String ID = makeID("GhostLash");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static int afterlife_inhand = 0;

    public GhostLash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 4;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "GhostLash.png");
    }

    @Override
    public void atTurnStartPreDraw() {
        super.atTurnStartPreDraw();
        calculate_bonus_damage();
    }

    public int calculate_bonus_damage(){
        if(!AbstractDungeon.overlayMenu.endTurnButton.enabled){
//        if(AbstractDungeon.actionManager.turnHasEnded){
            System.out.println("afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand afterlife_inhand ");
            System.out.println(afterlife_inhand);
            return afterlife_inhand * magicNumber;
        }else{
            afterlife_inhand = 0;
            for(AbstractCard c:AbstractDungeon.player.hand.group){
                if(c.hasTag(HexaMod.AFTERLIFE)){
                    afterlife_inhand += 1;
                }
            }
        }
        return afterlife_inhand * magicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += calculate_bonus_damage();
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += calculate_bonus_damage();
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

            dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
//        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, magicNumber), magicNumber));
    }

    @Override
    public void afterlife() {
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m == null) return;
//        this.calculateCardDamage(m);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }


    /*
    public void triggerOnGlowCheck() {
        this.glowColor = ExhaustCardTickPatch.exhaustedLastTurn ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;// 65
    }

     */

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(1);
        }
    }
}