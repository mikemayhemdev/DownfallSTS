package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

import java.util.ArrayList;

public class RapidStrikes extends AbstractChampCard {

    public final static String ID = makeID("RapidStrikes");

    private static final int DAMAGE = 4;

    public RapidStrikes() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        baseMagicNumber = magicNumber = 2;
        postInit();
        loadJokeCardImage(this, "RapidStrikes.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> strikeList = new ArrayList<>();
                for (AbstractCard q : AbstractDungeon.player.hand.group) {
                    if (q.hasTag(CardTags.STRIKE) && q.costForTurn > 0 && !q.freeToPlayOnce) {
                        strikeList.add(q);
                    }
                }
                if (!strikeList.isEmpty()) {
                    addToTop(new ReduceCostForTurnAction(strikeList.get(AbstractDungeon.cardRandomRng.random(strikeList.size() - 1)), 9));
                }
            }
        });
    }

    public void applyPowers() {
        super.applyPowers();

        this.rawDescription = cardStrings.DESCRIPTION;

        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        upgradeDamage(2);
    }
}