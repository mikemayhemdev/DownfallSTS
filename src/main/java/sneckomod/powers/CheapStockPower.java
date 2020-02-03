package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

import java.util.ArrayList;

public class CheapStockPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("CheapStockPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public CheapStockPower(final int amount) {
        this.name = "Cheap Stock";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        for (int i = 0; i < amount; i++)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    int x = 0;
                    for (AbstractCard q : AbstractDungeon.player.hand.group) {
                        if (q.color != TheSnecko.Enums.SNECKO_CYAN && q.cost > x)
                            x = q.cost;
                    }
                    ArrayList<AbstractCard> possCardsList = new ArrayList<>();
                    for (AbstractCard q : AbstractDungeon.player.hand.group) {
                        if (q.cost == x && q.color != TheSnecko.Enums.SNECKO_CYAN)
                            possCardsList.add(q);
                    }
                    if (!possCardsList.isEmpty()) {
                        flash();
                        AbstractCard q = possCardsList.get(AbstractDungeon.cardRandomRng.random(possCardsList.size() - 1));
                        q.modifyCostForCombat(-1);
                        q.superFlash();
                    }
                }
            });
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = "At the start of your turn, reduce the cost of the highest cost #yOffclass card in your hand by #b1 #b" + amount + " time.";
        else
            description = "At the start of your turn, reduce the cost of the highest cost #yOffclass card in your hand by #b1 #b" + amount + " times.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new CheapStockPower(amount);
    }
}