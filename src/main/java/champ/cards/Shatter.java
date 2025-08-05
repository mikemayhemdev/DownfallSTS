package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import hermit.cards.AbstractDynamicCard;

import static champ.ChampMod.loadJokeCardImage;

public class Shatter extends AbstractChampCard {

    public final static String ID = makeID("Shatter");

    //stupid intellij stuff skill, self, uncommon

    public Shatter() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 14;
        baseMagicNumber = magicNumber = 1;
        postInit();
        //tags.add(CardTags.STRIKE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
        loadJokeCardImage(this, "Shatter.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        if (bcombo() || dcombo() || !AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
            this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
        }
    }

    @Override
    public void triggerOnGlowCheck() { // it glows now.
        if (bcombo() || dcombo() || !AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID)) {
                this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
    }


    public void upp() {
        upgradeName();
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

}