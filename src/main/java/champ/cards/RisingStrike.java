package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RisingStrike extends AbstractChampCard {

    public final static String ID = makeID("RisingStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 9;

    public RisingStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        baseBlock = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() > 1)
            if (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).hasTag(ChampMod.FINISHER) && !this.purgeOnUse) {
                blck();
            }
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty())
            glowColor = (AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).hasTag(ChampMod.FINISHER) && !this.purgeOnUse ? AbstractCard.GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR);
    }

    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
    }
}