package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class RisingSlash extends AbstractChampCard {
    public final static String ID = makeID("RisingSlash");

    //Todo: rework to be more consistent.
    public RisingSlash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        tags.add(CardTags.STRIKE);
        loadJokeCardImage(this, "RisingSlash.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() > 1)
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).hasTag(ChampMod.FINISHER) && !this.purgeOnUse) {
                dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
            }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty())
            glowColor = (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).hasTag(ChampMod.FINISHER) && !this.purgeOnUse ? AbstractCard.GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR);
    }

    public void upp() {
        upgradeDamage(3);
    }
}