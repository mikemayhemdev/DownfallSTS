package awakenedOne.cards;

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class SiphonEnergy extends AbstractAwakenedCard {
    public final static String ID = makeID(SiphonEnergy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SiphonEnergy() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        magicNumber = baseMagicNumber = 4;
        loadJokeCardImage(this, makeBetaCardPath(SiphonEnergy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2) instanceof AbstractSpellCard) {
                    this.addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber));
                    if (m != null && !m.hasPower("Artifact")) {
                        this.addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber));
                        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber), magicNumber));
                    }
                    //atb(new DrawCardAction(AbstractDungeon.player, magicNumber));
                }
            }
        });
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1) instanceof AbstractSpellCard) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}