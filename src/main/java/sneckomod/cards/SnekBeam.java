package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

public class SnekBeam extends AbstractSneckoCard {

    public final static String ID = makeID("SnekBeam");

    //stupid intellij stuff ATTACK, ALL_ENEMY, UNCOMMON

    private static final int DAMAGE = -1;

    public SnekBeam() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
    }

    public static int countCards() {
        int i = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c instanceof AbstractUnknownCard) {
                i++;
            }
        }
        return i;
    }

    public void applyPowers() {
        this.baseDamage = countCards();
        super.applyPowers();// 48
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 49
        this.initializeDescription();// 50
    }// 51

    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);// 55
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];// 56
        this.initializeDescription();// 57
    }// 58

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal)));// 38
        atb(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));// 39
        this.rawDescription = cardStrings.DESCRIPTION;// 41
        this.initializeDescription();// 42
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}