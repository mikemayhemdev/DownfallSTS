package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import sneckomod.SneckoMod;
import theHexaghost.powers.BurnPower;
import theHexaghost.powers.EnhancePower;

public class RadiantFlame extends AbstractHexaCard {

    public final static String ID = makeID("RadiantFlame");

    public RadiantFlame() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 18;
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        HexaMod.loadJokeCardImage(this, "RadiantFlame.png");
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.getCurrRoom().monsters != null)
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped() && m.hasPower(BurnPower.POWER_ID)) {
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
                    return;
                }
            }

        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                if(m.hasPower(BurnPower.POWER_ID)){
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(magicNumber), magicNumber));
                    addToTop(new RemoveSpecificPowerAction(m, p, BurnPower.POWER_ID));
                }
                this.isDone = true;
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(6);
            upgradeMagicNumber(1);
        }
    }
}